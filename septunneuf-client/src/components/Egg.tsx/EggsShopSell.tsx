import { useEffect, useState } from "react";
import Egg from "./Egg";
import CustomAlert from "../Common/CustomAlert";
import CustomButton from "../Common/CustomButton";
import { useRecoilValue, useSetRecoilState } from "recoil";
import { heroState } from "../../recoil/HeroContext";
import { fetchShopEggsSell } from "../../services/apiShop";
import { fetchInventoryEggs } from "../../services/apiEggInventory";
import { incubateEggIdsState } from "../../recoil/IncubateEggIdsState";

const EggsShopSell = () => {
    const [eggIds, setEggIds] = useState([]);
    const [alertOpen, setAlertOpen] = useState(false);
    const [alertSeverity, setAlertSeverity] = useState("success"); 
    const hero = useRecoilValue(heroState);
    const setHeroState = useSetRecoilState(heroState); 
    const incubateEggIds = useRecoilValue(incubateEggIdsState);


    useEffect(() => {
        const fetchData = async () => {
            const data = await fetchInventoryEggs(hero);
            if (data !== null) {
                //setEggIds(data.eggIds);

                // Ne pas afficher les oeufs déjà incubés
                const filteredIds = data.eggIds.filter((id: number) => !incubateEggIds.includes(id));
                setEggIds(filteredIds);
            }
        };
        fetchData();
    }, []);
    
    const handleSellEgg = async (eggId: number) => {
        const data = await fetchShopEggsSell(hero, eggId);
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
                {eggIds.map((eggId: number) => (
                    <div key={eggId} className="text-center rounded-md m-2">
                        <Egg eggId={eggId}/>
                        <CustomButton
                            onClick={() => handleSellEgg(eggId)}
                            text="Vendre"
                            color="red"
                        />
                    </div>
                ))}
            </div>
            <CustomAlert 
                severty="success"
                text={
                    alertSeverity === "success" ? "Vente de l'oeuf réussi !" : "Erreur lors de la vente de l'oeuf"
                }
                open={alertOpen} 
                onClose={handleCloseAlert} 
            />
        </div>
    );
};

export default EggsShopSell;