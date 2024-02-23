import { Modal } from "@mui/material";
import { useCallback, useEffect, useState } from "react";
import { fetchInventoryEggs } from "../../services/apiEggInventory";
import { useRecoilValue } from "recoil";
import { heroState } from "../../recoil/HeroContext";
import Egg from "../Egg.tsx/Egg";
import CustomButton from "../Common/CustomButton";
import { fetchEggsDetails } from "../../services/apiEgg";
import { fillIncubator } from "../../services/apiIncubator";
import { incubateEggIdsState } from "../../recoil/IncubateEggIdsState";

const BagEggsModal = ({ open, onClose, incubatorId }: { open: boolean; onClose: () => void; incubatorId: number }, ) => {
    const hero = useRecoilValue(heroState);
    const incubateEggIds = useRecoilValue(incubateEggIdsState);

    const [eggIds, setEggIds] = useState<number[]>([]);
    const [egg, setEgg] = useState<any | null>([]);

    useEffect(() => {
        const getEggIds = async () => {
            const data = await fetchInventoryEggs(hero); 
            //setEggIds(data.eggIds); 

            // Ne pas afficher les oeufs déjà incubés
            const filteredIds = data.eggIds.filter((id: number) => !incubateEggIds.includes(id));
            setEggIds(filteredIds); 
        }
        getEggIds();
    }, []);
    
    const getEggDetails = useCallback(async (eggId: number) => {
        const fetchEggDetails = async () => {
            const data = await fetchEggsDetails(eggId); 
            setEgg(data); 
        }
        fetchEggDetails();
    }, [eggIds]);
    
    const handleIncubateEgg = useCallback(async (eggId: number) => {
        getEggDetails(eggId);
        
        const data = fillIncubator(egg.incubationTime, incubatorId, eggId);
        if (data !== null) {
            console.log('Egg incubated');
        }
    }, [getEggDetails]);

    return (
        <>
             <Modal
                open={open}
                onClose={onClose}
                aria-labelledby="modal-title"
                aria-describedby="modal-description"
            >
                <div className="absolute top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 w-400 bg-white shadow-lg p-4 rounded-md">
                <h1 className="font-bold text-2xl my-2">Liste des oeufs pour incubation</h1>
                   {eggIds.map((eggId: any) => (
                    <div key={eggId} className="text-center rounded-md m-2">
                        <Egg eggId={eggId}/>
                        <CustomButton
                            onClick={() => handleIncubateEgg(eggId)}
                            text="Incuber"
                            color="green"
                        />
                    </div>
                ))}
                </div>
            </Modal>
        </>
    );
};

export default BagEggsModal;