import { useEffect, useState } from "react";
import BagEgg from "./BagEgg";
import BagEggsModal from "./BagEggsModal";

const BagIncubator = (props:{incubatorId: number}) => {
    const [incubator, setIncubator] = useState<any | null>([]);
    const [openModal, setOpenModal] = useState(false); 
    
    useEffect(() => {
        const fetchIncubatorStatus = async (incubatorId: number) => {
            try {
                const response = await fetch(`${process.env.REACT_APP_API_URL}/incubators/${incubatorId}/status`, {
                    method: 'GET',
                    headers: {
                        'Content-Type': 'application/json',
                    }
                });
                if (!response.ok) {
                    console.log('Erreur lors de la récupération des données');
                }
                const data = await response.json();
                console.log("fetchIncubatorStatus data:", data[0]);
                setIncubator(data[0]);
            } 
            catch (error) {
                console.error('Erreur:', error);
            }
        };
        fetchIncubatorStatus(props.incubatorId);
    }, []);

    const handleOpenModal = () => {
        setOpenModal(true);
    };

    const handleCloseModal = () => {
        setOpenModal(false);
    };

    return(
        <>
            <div className="bg-gray-100 p-4 rounded-md">
                <div>
                    {
                        incubator.eggId ? (
                            <div className="">
                                Incubation en cours...
                            </div>    
                        ) : (
                            <div>
                                Incubation restante: {incubator.durability}
                            </div>
                        )
                    } 
                </div>
                <div className="">
                    {
                        incubator.eggId ? (
                            <div className="">
                                <BagEgg 
                                    eggId={incubator.eggId}
                                />
                            </div>    
                        ) : (
                            <div className="text-center">
                                <button onClick={handleOpenModal} className="bg-green-300 px-4 py-2 mt-2 rounded-md hover:bg-green-400">ajouter un oeuf</button>
                            </div>
                        )
                    }
                </div>
            </div>
            <BagEggsModal open={openModal} onClose={handleCloseModal} />
        </>
    );
};

export default BagIncubator;