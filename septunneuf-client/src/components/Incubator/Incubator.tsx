import { useEffect, useState } from "react";
import CustomButton from "../Common/CustomButton";
import CustomAlert from "../Common/CustomAlert";

const Incubator = () => {
    const [incubators, setIncubators] = useState([]);
    const [alertOpen, setAlertOpen] = useState(false);
    const [alertSeverity, setAlertSeverity] = useState("success"); 

    useEffect(() => {
        const fetchIncutbators = async () => {
            try {
                const response = await fetch(`${process.env.REACT_APP_API_URL}/incubators`);
                if (!response.ok) {
                    console.log('Erreur lors de la récupération des données');
                }
                const data = await response.json();
                setIncubators(data);
                } 
            catch (error) {
                console.error('Erreur:', error);
            }
        };
        fetchIncutbators();
    }, [])

    const handleBuyIncubator = async () => {
        setAlertOpen(true);
        try {
            const response = await fetch(`${process.env.REACT_APP_API_URL}/incubators/create`, {
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
        <>
            <div className="bg-gray-100 p-4 my-4 rounded-md shadow-sm inline-block">
            <h1 className="font-bold text-2xl my-2">Incubateurs possédés: {incubators.length}/6</h1>
                    <CustomButton 
                        onClick={handleBuyIncubator} 
                        text="Ajouter un incubateur" 
                        disabled={incubators.length < 6 ? false: true} 
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