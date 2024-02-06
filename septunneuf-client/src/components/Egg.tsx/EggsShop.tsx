import { useEffect, useState } from "react";
import Egg from "./Egg";
import CustomAlert from "../Common/CustomAlert";
import CustomButton from "../Common/CustomButton";

interface EggsShopProps {
    CustomButtonLabel?: string;
    color?: string;
    sellOrBuy?: boolean;
}

const EggsShop = (props: EggsShopProps) => {
    const [eggs, setEggs] = useState([]);
    const [alertOpen, setAlertOpen] = useState(false);
    const [alertSeverity, setAlertSeverity] = useState("success"); 

    useEffect(() => {
        const fetchEggItemsShop = async () => {
            try {
                console.log(props.sellOrBuy)
                const url = props.sellOrBuy
                    ? `${process.env.REACT_APP_API_URL}/inventory/eggs`
                    : `${process.env.REACT_APP_API_URL}/shop/items`;
                const response = await fetch(url);
                if (!response.ok) {
                    console.log('Erreur lors de la récupération des données');
                }
                const data = await response.json();
                setEggs(data);
                } 
            catch (error) {
                console.error('Erreur:', error);
            }
        };
        fetchEggItemsShop();
    }, [])

    const handleBuyEgg = async (eggId: number) => {
        setAlertOpen(true);
        try {
            const url = props.sellOrBuy 
                ? `${process.env.REACT_APP_API_URL}/shop/eggs/${eggId}/sell` 
                : `${process.env.REACT_APP_API_URL}/shop/eggs/${eggId}/purchase`;

            const response = await fetch(url, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({id: 1})
            });
            if (!response.ok) {
                setAlertSeverity("error");
            } else {
                setAlertSeverity("success");
            }
            const data = await response.json();
        } 
        catch (error) {
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
                        <Egg data={egg}/>
                        <CustomButton 
                            onClick={() => handleBuyEgg(egg.id)}
                            text={props.CustomButtonLabel  }
                            color={props.color}
                        />
                    </div>
                ))}
            </div>
            <CustomAlert 
                severty="error"
                text={
                    alertSeverity === "success" ? 
                        props.sellOrBuy ? 
                            "Vente de l'oeuf réussi !" 
                            : "Achat de l'oeuf réussi !"
                        : props.sellOrBuy ?
                            "Erreur lors de la vente de l'oeuf"
                            :"Erreur lors de l'achat de l'oeuf"
                }
                open={alertOpen} 
                onClose={handleCloseAlert} 
            />
        </div>
    );
};

export default EggsShop;