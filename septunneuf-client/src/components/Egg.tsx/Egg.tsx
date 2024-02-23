import { useEffect, useState } from "react";
import { fetchEggsDetails } from "../../services/apiEgg";

const Egg = (props:{eggId: number, price?: number}) => {

    const [egg, setEgg] = useState<any | null>([]);

    useEffect(() => {
        const fetchEgg = async (eggId: number) => {
            const data = await fetchEggsDetails(eggId);
            if (data !== null) {
                setEgg(data);
            };
        }
        fetchEgg(props.eggId);
    }, []);

    return (
        <div className="flex flex-col items-center justify-center">
            <div style={{ background: egg?.color}} className="m-2 mt-4 mb-0 w-32 h-32 rounded-full flex items-center justify-center" >
                {props.price && `${props.price}€`}
            </div>
        </div>
    );
}

export default Egg;