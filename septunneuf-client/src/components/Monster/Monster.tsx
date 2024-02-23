import { useEffect, useState } from "react";
import { fetchMonsterDetails } from "../../services/apiMonster";

const Monster = (props:{monsterId: number}) => {
    const { monsterId } = props;
    const [monster, setMonster] = useState<any>();
    
    useEffect(() => {
        const fetchData = async () => {
            const data = await fetchMonsterDetails(monsterId);
            if (data !== null) {    
                setMonster(data);
            }
        };
        fetchData();
    }, []);

    return (
        <div className="flex flex-col items-center justify-center">
            <div style={{ background: monster?.color}} className="m-2 mt-4 mb-0 w-32 h-32" /> 
            <div className="font-bold">{monster?.name}</div>
        </div>
    );
}

export default Monster;