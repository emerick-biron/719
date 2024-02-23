import { HeroType } from "../recoil/HeroContext";

// /inventory/eggs
export const fetchInventoryEggs = async (hero: HeroType | null) => {
    try {
        const response = await fetch(`${process.env.REACT_APP_API_URL}/inventory/eggs`, {
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