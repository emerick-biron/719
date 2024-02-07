import { useEffect, useState } from "react";
import BagTeamMonster from "./BagTeamMonster";

const BagTeam = () => {
    const [inventoryMonsters, setInventoryMonsters] = useState([]);

    const fetchInventoryMonsters = async () => {
        try {
            const response = await fetch(`${process.env.REACT_APP_API_URL}/inventory/monsters`);
            if (!response.ok) {
                console.log('Erreur lors de la récupération des données');
            }
            const data = await response.json();
            setInventoryMonsters(data);
            } 
        catch (error) {
            console.error('Erreur:', error);
        }
    };

    useEffect(() => {
        fetchInventoryMonsters();
    }, [])

    const handleDelete = async () => {
        await fetchInventoryMonsters();
    };

    return (
        <>
            <h1 className="font-bold text-2xl my-2">Mon équipe: {inventoryMonsters.length}/6</h1>
            <div className="flex flex-col">
                {
                    inventoryMonsters.map((monster: any, value) => (
                        <BagTeamMonster 
                            key={monster.id}
                            data={monster}
                            onDelete={handleDelete}
                        />
                    ))
                }
            </div>
        </>
    );
};

export default BagTeam;