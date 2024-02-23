
import { useEffect, useState } from "react";
import Egg from "./Egg";
import CustomAlert from "../Common/CustomAlert";
import CustomButton from "../Common/CustomButton";
import { useRecoilValue, useSetRecoilState } from "recoil";
import { heroState } from "../../recoil/HeroContext";
import { fetchShopEggs, fetchShopEggsPurchase } from "../../services/apiShop";

const EggsShopBuy = () => {
    const [eggs, setEggs] = useState([]);
    const [alertOpen, setAlertOpen] = useState(false);
    const [alertSeverity, setAlertSeverity] = useState("success"); 
    const hero = useRecoilValue(heroState);
    const setHeroState = useSetRecoilState(heroState); 

    useEffect(() => {
        const fetchData = async () => {
            const data = await fetchShopEggs();
            if (data !== null) {
                setEggs(data.eggs);
            }
        };
        fetchData();
    }, []);
    
    const handleBuyEgg = async (eggId: number) => {
        setAlertOpen(true);
        const data = await fetchShopEggsPurchase(hero, eggId);
        if (data !== null) {
            setAlertSeverity("success");
            if (data.money !== undefined) {
                setHeroState((prevHeroState) => {
                    if (prevHeroState === null) {
                        return prevHeroState; 
                    }
                    const updatedHeroState = {
                        ...prevHeroState,
                        money: data.money
                    };
                    localStorage.setItem('hero', JSON.stringify(updatedHeroState)); 
                    return updatedHeroState;
                });
            }
        } else {
            setAlertSeverity("error");
        }        
    };

    const handleCloseAlert = () => {
        setAlertOpen(false);
    };
    
    return (
        <div className="bg-gray-100 rounded-md p-4 my-4 inline-block">
            <h1 className="font-bold text-2xl">Oeufs</h1>
            <div className="flex flex-wrap">
                {eggs.map((egg: any) => (
                    <div key={egg.id} className="text-center rounded-md m-2">
                        <Egg eggId={egg.id} price={egg.price}/>
                        <CustomButton 
                            onClick={() => handleBuyEgg(egg.id)}
                            text="Acheter"
                            color="green"
                        />
                    </div>
                ))}
            </div>
            <CustomAlert 
                severty="success"
                text={
                    alertSeverity === "success" ? "Achat de l'oeuf réussi !":"Erreur lors de l'achat de l'oeuf"
                }
                open={alertOpen} 
                onClose={handleCloseAlert} 
            />
        </div>
    );
};
export default EggsShopBuy;