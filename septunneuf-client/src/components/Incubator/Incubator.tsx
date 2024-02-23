import { useEffect, useState } from "react";
import CustomButton from "../Common/CustomButton";
import CustomAlert from "../Common/CustomAlert";
import { useRecoilValue, useSetRecoilState } from "recoil";
import { heroState } from "../../recoil/HeroContext";
import { fetchShopIncubator } from "../../services/apiShop";
import { fetchIncubators } from "../../services/apiIncubator";

const Incubator = () => {
    const [incubatorIds, setIncubatorIds] = useState([]);
    const [alertOpen, setAlertOpen] = useState(false);
    const [alertSeverity, setAlertSeverity] = useState("success"); 
    const hero = useRecoilValue(heroState);
    const setHeroState = useSetRecoilState(heroState); 


    useEffect(() => {
        const fetchData = async () => {
            const data = await fetchIncubators(hero);
            if (data !== null) {
                setIncubatorIds(data.incubatorIds);
            } 
        };
        fetchData();
    }, [])


    const handleBuyIncubator = async () => {
        setAlertOpen(true);
        const data = await fetchShopIncubator(hero);
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
        <>
            <div className="bg-gray-100 p-4 my-4 rounded-md shadow-sm inline-block">
            <h1 className="font-bold text-2xl my-2">Incubateurs possédés: {incubatorIds.length}/6</h1>
                <CustomButton 
                    onClick={handleBuyIncubator} 
                    text="Acheter un incubateur" 
                    disabled={incubatorIds.length < 6 ? false: true} 
                    color="green"
                />
            </div>
            <CustomAlert 
                severty="error"
                text={alertSeverity === "success" ? "Achat d'incubateur réussi !" : "Erreur lors de l'achat d'incubateur"}
                open={alertOpen} 
                onClose={handleCloseAlert} 
            />
        </>
      );
}

export default Incubator;