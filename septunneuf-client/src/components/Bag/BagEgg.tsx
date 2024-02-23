import { useEffect, useState } from "react";
import { fetchEggsDetails } from "../../services/apiEgg";

const BagEgg = (props:{eggId: number}) => {
    const { eggId } = props
    const [egg, setEgg] = useState<any | null>([]);

    useEffect(() => {
        const fetchData = async (eggId: number) => {
            const data = await fetchEggsDetails(eggId);
            if (data !== null) {
                setEgg(data);
            }
        };
        fetchData(eggId);
    }, [eggId]);

    return(
        <div className="flex flex-col items-center justify-center">
            <div style={{ background: egg?.color}} className="m-2 mt-4 mb-0 w-32 h-32 rounded-full flex items-center justify-center" >
                {egg?.incubationTime}
            </div>
        </div>
    );
}

export default BagEgg;