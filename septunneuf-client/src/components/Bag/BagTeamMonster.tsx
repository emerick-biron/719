import { useRecoilValue } from "recoil";
import CustomButton from "../Common/CustomButton"
import { heroState } from "../../recoil/HeroContext";
import { useCallback, useEffect, useState } from "react";
import { fetchInventoryMonstersRelease, fetchInventoryMonstersStore } from "../../services/apiMonsterInventory";
import { fetchMonsterDetails } from "../../services/apiMonster";

const BagTeamMonster = (props:{monsterId: number}) => {

    const hero = useRecoilValue(heroState);
    const [monster, setMonster] = useState<any>();

    useEffect(() => {
        const fetchData = async () => {
            const data = await fetchMonsterDetails(props.monsterId);
            if (data !== null) {    
                setMonster(data);
            }
        };
        fetchData();
    }, []);


    const handleMonsterStore = useCallback(async (monsterId: number) => {
        fetchInventoryMonstersStore(hero, monsterId);
    }, [props.monsterId, hero?.name]);

    const handleMonsterRelease = useCallback(async (monsterId: number) => {
        fetchInventoryMonstersRelease(hero, monsterId);
    }, [props.monsterId, hero?.name]);

    return(
        <div style={{ border: `2px solid ${monster?.color}` }} className="w-1/2 px-2 my-2 flex items-center rounded-md">
            <div className="font-semibold pl-2">{props.monsterId} - {monster?.name.toUpperCase()}</div>
            <div className="ml-auto">

                <CustomButton 
                    onClick={() => handleMonsterStore(props.monsterId)}
                    text="Stocker"
                    color="green"
                />
                <CustomButton 
                    onClick={() => handleMonsterRelease(props.monsterId)}
                    text="Libérer"
                    color="red"
                />
            </div>
        </div>
    );
};

export default BagTeamMonster;