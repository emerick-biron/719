import { useEffect, useState } from "react";
import { useRecoilState } from "recoil";
import { heroState } from "../../recoil/HeroContext";
import BagIncubator from "./BagIncubator";
import { fetchIncubators } from "../../services/apiIncubator";
import { incubateEggIdsState } from "../../recoil/IncubateEggIdsState";

const BagIncubatorView = () => {
    const [incubatorsIds, setIncubatorsIds] = useState<number[]>([]);
    const [hero, setHero] = useRecoilState(heroState);
    const [, setIncubateEggIds] = useRecoilState(incubateEggIdsState);

    useEffect(() => {
        const fetchData = async () => {
            const data = await fetchIncubators(hero);
            if (data !== null) {
                setIncubatorsIds(data.incubatorIds);
            }
        };
        fetchData();
        // Vider le tableau incubateEggIdsState
        setIncubateEggIds([]);
    }, []);

    return(
        <>
            <h1 className="font-bold text-2xl my-2">Mes incubateurs: {incubatorsIds.length}/6</h1>
            <div  className="grid grid-cols-3 gap-4">
                {
                    incubatorsIds.map((incubatorsId, index) => (
                        <BagIncubator 
                            key={index} 
                            incubatorId={incubatorsId}
                        />
                    ))
                }
            </div>
        </>
    )
}

export default BagIncubatorView;
