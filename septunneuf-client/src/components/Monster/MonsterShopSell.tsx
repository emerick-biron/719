import { useEffect, useState } from "react";
import { fetchInventoryMonsters } from "../../services/apiMonsterInventory";
import { useRecoilValue, useSetRecoilState } from "recoil";
import { heroState } from "../../recoil/HeroContext";
import { fetchStorage } from "../../services/apiMonsterStorage";
import Monster from "./Monster";
import CustomButton from "../Common/CustomButton";
import { fetchShopMonstersSell } from "../../services/apiShop";

const MonsterShopSell = () => {
    const hero = useRecoilValue(heroState);
    const setHeroState = useSetRecoilState(heroState); 
    const [inventoryMonsterIds, setInventoryMonsterIds] = useState([]);
    const [storageMonsterIds, setStorageMonsterIds] = useState([]);

    useEffect(() => {
        const fetchInventoryData = async () => {
            const data = await fetchInventoryMonsters(hero);
            if (data !== null) {
                setInventoryMonsterIds(data.monsterIds);
            }
        };
        fetchInventoryData();

        const fetchStorageData = async () => {
            const data = await fetchStorage(hero);
            if (data !== null) {
                setStorageMonsterIds(data.monsterIds);
            }
        };
        fetchStorageData();
    }, []);

    const handleSellMonster = async (monsterId: number) => {
        const data = await fetchShopMonstersSell(hero, monsterId);
        if (data !== null) {
            if (data.money !== undefined) {
                setHeroState((prevHeroState) => {
                    if (prevHeroState === null) {
                        return prevHeroState; 
                    }
                    const updatedHeroState = {
                        ...prevHeroState,
                        money: data.money
                    };
                    localStorage.setItem('hero', JSON.stringify(updatedHeroState)); 
                    return updatedHeroState;
                });
            }
        }
    };

    return (
        <>
            <div className="bg-gray-100 rounded-md p-4 my-4 inline-block">
                <h1 className="font-bold text-2xl">Monstres - Sac</h1>
                <div className="flex flex-wrap">
                    {inventoryMonsterIds.map((monsterId: number) => (
                        <div key={monsterId} className="text-center rounded-md m-2">
                            <Monster monsterId={monsterId}/>
                            <CustomButton
                                onClick={() => handleSellMonster(monsterId)}
                                text="Vendre"
                                color="red"
                            />
                        </div>
                    ))}
                </div>
            </div>

            <div className="bg-gray-100 rounded-md p-4 my-4 inline-block">
                <h1 className="font-bold text-2xl">Monstres - Inventaire</h1>
                <div className="flex flex-wrap">
                    {storageMonsterIds.map((monsterId: number) => (
                        <div key={monsterId} className="text-center rounded-md m-2">
                            <Monster monsterId={monsterId}/>
                            <CustomButton
                                onClick={() => handleSellMonster(monsterId)}
                                text="Vendre"
                                color="red"
                            />
                        </div>
                    ))}
                </div>
            </div>
        </>
    );
};

export default MonsterShopSell;