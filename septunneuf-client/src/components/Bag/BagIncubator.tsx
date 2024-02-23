import { useEffect, useState } from "react";
import BagEgg from "./BagEgg";
import BagEggsModal from "./BagEggsModal";
import { fetchIncubatorsStatus } from "../../services/apiIncubator";
import { useRecoilState } from "recoil";
import { incubateEggIdsState } from "../../recoil/IncubateEggIdsState";

const BagIncubator = (props:{incubatorId: number}) => {
    const {incubatorId} = props;

    const [incubator, setIncubator] = useState<any | null>([]);
    const [openModal, setOpenModal] = useState(false); 
    const [incubateEggIds, setIncubateEggIds] = useRecoilState(incubateEggIdsState);

    useEffect(() => {
        const fetchData = async () => {
            const data = await fetchIncubatorsStatus(incubatorId);
            if (data !== null) {    
                setIncubator(data);
                if(data.eggId !== undefined) {
                    setIncubateEggIds((prevIncubateEggIds) => {
                        // Vérifier si l'identifiant de l'œuf existe déjà dans le tableau
                        if (prevIncubateEggIds.map(id => id).indexOf(data.eggId) === -1) {
                            return [...prevIncubateEggIds, data.eggId];
                        }
                        return prevIncubateEggIds;
                    });
                }
            }
        };
        fetchData();
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
                        incubator.incubatorStatus === 'FULL' ? (
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
                        incubator.incubatorStatus === "FULL" ? (
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
            <BagEggsModal incubatorId={incubatorId} open={openModal} onClose={handleCloseModal} />
        </>
    );
};

export default BagIncubator;