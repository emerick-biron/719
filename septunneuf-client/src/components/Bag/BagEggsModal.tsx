import { Modal } from "@mui/material";
import { useCallback, useEffect, useMemo, useState } from "react";
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

            // Ne pas afficher les oeufs déjà incubés
            const filteredIds = data.eggIds.filter((id: number) => !incubateEggIds.includes(id));
            setEggIds(filteredIds); 
        }
        getEggIds();
    }, []);
    
    const getEggDetails = useCallback(async(eggId: number) => {
        const fetchEggDetails = async () => {
            const data = await fetchEggsDetails(eggId); 
            setEgg(data); 
            handleIncubateEgg(data)
        }
        fetchEggDetails();
    }, []);

    const handleIncubateEgg = useCallback(async (egg: any) => {
        const data = await fillIncubator(egg.incubationTime, incubatorId, egg.id, hero);
        if (data !== null) {
            console.log('Egg incubated', data);
        }
        window.location.reload();
    }, [egg]);

    return (
        <>
<Modal
    open={open}
    onClose={onClose}
    aria-labelledby="modal-title"
    aria-describedby="modal-description"
>
    <div className="absolute top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 bg-white shadow-lg p-6 rounded-md" style={{ width: '800px', maxWidth: '90vw', maxHeight: '80vh', overflowY: 'auto' }}>
        <h1 className="font-bold text-3xl mb-4">Liste des œufs pour incubation</h1>
        <div className="grid grid-cols-3 gap-4">
            {eggIds.map((eggId: any) => (
                <div key={eggId} className="flex flex-col items-center">
                    <Egg eggId={eggId} />
                    <CustomButton
                        onClick={() => getEggDetails(eggId)}
                        text="Incuber"
                        color="green"
                    />
                </div>
            ))}
        </div>
    </div>
</Modal>

        </>
    );
};

export default BagEggsModal;