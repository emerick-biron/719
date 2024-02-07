import { useEffect, useState } from "react";
import { useRecoilState } from "recoil";
import { heroState } from "../../recoil/HeroContext";
import BagIncubator from "./BagIncubator";

const BagIncubatorView = () => {
    const [incubatorsIds, setIncubatorsIds] = useState<number[]>([]);
    const [hero, setHero] = useRecoilState(heroState);
    
    useEffect(() => {
        const fetchIncubatorsIds = async () => {
            try {
                const response = await fetch(`${process.env.REACT_APP_API_URL}/incubators`, {
                    method: 'GET',
                    headers: {
                        'Content-Type': 'application/json',
                        'heroName': hero?.name ? hero.name : 'louis',
                    }
                });
                if (!response.ok) {
                    console.log('Erreur lors de la récupération des données');
                }
                const data = await response.json();
                console.log("fetchIncubatorsIds data:", data);
                setIncubatorsIds(data);
            } 
            catch (error) {
                console.error('Erreur:', error);
            }
        };
        fetchIncubatorsIds();
    }, []);

    return(
        <>
            <h1 className="font-bold text-2xl my-2 ">Mes incubateurs: {incubatorsIds.length}/6</h1>
            <div className="flex">
                {
                    incubatorsIds.map((incubatorsId, index) => (
                        <div>
                            <BagIncubator 
                                key={index} 
                                incubatorId={incubatorsId}
                            />
                        </div>
                    ))
                }
            </div>
        </>
    )
}

export default BagIncubatorView;
