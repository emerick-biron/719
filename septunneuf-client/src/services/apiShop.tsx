import { HeroType } from "../recoil/HeroContext";

// /shop/monsters/{monsterId}/sell
export const fetchShopMonstersSell = async (hero: HeroType | null, monsterId: number) => {
    try {
        const response = await fetch(`${process.env.REACT_APP_API_URL}/shop/monsters/${monsterId}/sell`, {
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

// /shop/incubator
export const fetchShopIncubator = async (hero: HeroType | null) => {
    let alertStatus: string;

    try{
        const response = await fetch(`${process.env.REACT_APP_API_URL}/shop/incubator`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                heroName : hero?.name || 'louis',     
            }
        });
        if (!response.ok) {
            console.log('Erreur lors de la récupération des données');
            return null
        } 
        const data = await response.json();
        return data;
    } 
    catch (error) {
        console.error('Erreur:', error);
        return null;
    }
};

// /shop/eggs/{eggId}/sell
export const fetchShopEggsSell = async (hero: HeroType | null, eggId: number) => {
    try {
        const response = await fetch(`${process.env.REACT_APP_API_URL}/shop/eggs/${eggId}/sell`, {
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

// /shop/eggs/{eggId}/purchase
export const fetchShopEggsPurchase = async (hero: HeroType | null, eggId: number) => {
    try {
        const response = await fetch(`${process.env.REACT_APP_API_URL}/shop/eggs/${eggId}/purchase`, {
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

// /shop/eggs
export const fetchShopEggs = async () => {
    try {
        const response = await fetch(`${process.env.REACT_APP_API_URL}/shop/eggs`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
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