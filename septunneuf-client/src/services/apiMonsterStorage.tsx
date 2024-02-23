import { HeroType } from "../recoil/HeroContext";

// /storage
export const fetchStorage = async (hero: HeroType | null) => {
    try {
        const response = await fetch(`${process.env.REACT_APP_API_URL}/storage`, {
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