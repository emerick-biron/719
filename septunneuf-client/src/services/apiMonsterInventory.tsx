import { HeroType } from "../recoil/HeroContext";

// /inventory/monsters/{monsterId}/store
export const fetchInventoryMonstersStore = async (hero: HeroType | null, monsterId: number) => {
    try {
        const response = await fetch(`${process.env.REACT_APP_API_URL}/inventory/monsters/${monsterId}/store`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                heroName: hero?.name || 'louis',
            }
        });
        if (!response.ok) {
            console.log('Erreur lors de la récupération des données');
            return null; 
        }
        const data = await response.json();
        return data;
    } 
    catch (error) {
        console.error('Erreur:', error);
        return null;
    }
};
// /inventory/monsters/{monsterId}/release
export const fetchInventoryMonstersRelease = async (hero: HeroType | null, monsterId: number) => {
    try {
        const response = await fetch(`${process.env.REACT_APP_API_URL}/inventory/monsters/${monsterId}/release`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                heroName: hero?.name || 'louis',
            }
        });
        if (!response.ok) {
            console.log('Erreur lors de la récupération des données');
            return null; 
        }
        const data = await response.json();
        return data;
    } 
    catch (error) {
        console.error('Erreur:', error);
        return null;
    }
};

// /inventory/monsters
export const fetchInventoryMonsters = async (hero: HeroType | null) => {
    try {
        const response = await fetch(`${process.env.REACT_APP_API_URL}/inventory/monsters`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                heroName: hero?.name || 'louis',
            }
        });
        if (!response.ok) {
            console.log('Erreur lors de la récupération des données');
            return null; 
        }
        const data = await response.json();
        return data;
    } 
    catch (error) {
        console.error('Erreur:', error);
        return null;
    }
};
