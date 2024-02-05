import { useEffect, useState } from "react";
import Button from "./CustomButton";
import CustomAlert from "./CustomAlert";

const Incubator = () => {
    const [incubators, setIncubators] = useState([]);
    const [alertOpen, setAlertOpen] = useState(false);
    const [alertSeverity, setAlertSeverity] = useState("success"); // Ajout de l'état de la gravité de l'alerte


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
    }, [incubators])

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
                console.log('Erreur lors de la récupération des données');
                setAlertSeverity("error");
            } else {
                setAlertSeverity("success");
            }
            const data = await response.json();
        } 
        catch (error) {
            console.error('Erreur:', error);
            setAlertSeverity("error");
        }
    };
    
    const handleCloseAlert = () => {
        setAlertOpen(false);
    };

    return (
        <>
            <div className="bg-gray-200 m-2 p-4 rounded-md w-1/4 shadow-sm">
                <div className="flex items-center">
                    <div className="w-20 h-20 bg-gray-300 mr-4"></div>
                    <div>
                        <p>Incubateurs possédés: {incubators.length}/6</p>
                    </div>
                </div>
                <Button 
                    onClick={handleBuyIncubator} 
                    text="Ajouter un oeuf" 
                    disabled={incubators.length < 6 ? false: true} 
                    color="#6AFF82"
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