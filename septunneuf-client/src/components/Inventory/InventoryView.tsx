import React, { useEffect, useState } from "react";
import Egg from "../Egg.tsx/Egg";
import Monster from "../Monster/Monster";
import { useRecoilValue } from "recoil";
import { heroState } from "../../recoil/HeroContext";
import { fetchStorage } from "../../services/apiMonsterStorage";

const InvetoryView = () => {

    const hero = useRecoilValue(heroState);
	const [monsterIds, setMonsterIds] = useState([]);

    useEffect(() => {
        const fetchData = async () => {
            const data = await fetchStorage(hero);
            if (data !== null) {    
                setMonsterIds(data.monsterIds);
            }
        };
        fetchData();
    }, [hero]);

    return (
        <div className="pt-20 px-20">
			<div className="bg-gray-100 rounded-md p-4 my-4 inline-block">
				<h1 className="text-2xl font-bold mt-4">Monstres: {monsterIds.length}</h1>
				<div className="flex flex-wrap">
					{monsterIds.map((monsterId: number, index) => (
						<Monster key={index} monsterId={monsterId}/>
					))}
				</div>
			</div>
        </div>
    )
}

export default InvetoryView;