import React, { useEffect, useState } from "react";
import Egg from "../Common/Egg";
import Monster from "../Common/Monster";

const InvetoryView = () => {

    const [eggs, setEggs] = useState([]);
	const [monsters, setMonsters] = useState([]);
    useEffect(() => {
        const fetchEggInventory = async () => {
          try {
            const response = await fetch(`${process.env.REACT_APP_API_URL}/inventory/eggs`);
            if (!response.ok) {
              console.log('Erreur lors de la récupération des données');
            }
            const data = await response.json();
            setEggs(data);
          } catch (error) {
            console.error('Erreur:', error);
          }
        };
        fetchEggInventory();

        const fetchMonsters = async () => {
          try {
            const response = await fetch(`${process.env.REACT_APP_API_URL}/inventory/monsters`);
            if (!response.ok) {
              console.log('Erreur lors de la récupération des données');
            }
            const data = await response.json();
            setMonsters(data);
          } catch (error) {
            console.error('Erreur:', error);
          }
        };
        fetchMonsters();
      }, []);

    return (
        <div className="pt-20">
            <h1 className="text-2xl font-bold">Oeufs - {eggs.length}</h1>
            <div className="flex flex-wrap">
              {eggs.map((egg: any) => (
                <Egg key={egg.id} data={egg}/>
              ))}
            </div>
			<h1 className="text-2xl font-bold mt-4">Monstres - {monsters.length}</h1>
            <div className="flex flex-wrap">
				{monsters.map((monster: any) => (
					<Monster key={monster.id} data={monster}/>
				))}
            </div>
        </div>
    )
}

export default InvetoryView;