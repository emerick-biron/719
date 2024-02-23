import { useCallback, useEffect, useState } from "react";
import BagTeamMonster from "./BagTeamMonster";
import { useRecoilValue } from "recoil";
import { heroState } from "../../recoil/HeroContext";

const BagTeam = () => {
    const [monsterIds, setMonsterIds] = useState([]);
    const hero = useRecoilValue(heroState);


    const fetchInventoryMonsters = useCallback(async () => {
        try {
            const response = await fetch(`${process.env.REACT_APP_API_URL}/inventory/monsters`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    heroName: hero?.name ? hero.name : 'louis',
                }
                });
            if (!response.ok) {
                console.log('Erreur lors de la récupération des données');
            }
            const data = await response.json();
            setMonsterIds(data.monsterIds);
            } 
        catch (error) {
            console.error('Erreur:', error);
        }
    }, [hero?.name]);

    useEffect(() => {
        fetchInventoryMonsters();
    }, [])

    return (
        <>
            <h1 className="font-bold text-2xl my-2">Mon équipe: {monsterIds.length}/6</h1>
            <div className="flex flex-col">
                {
                    monsterIds.map((monsterId: number, index) => (
                        <BagTeamMonster 
                            key={index}
                            monsterId={monsterId}
                        />
                    ))
                }
            </div>
        </>
    );
};

export default BagTeam;