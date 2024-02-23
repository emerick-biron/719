import { HeroType } from "../recoil/HeroContext";

// /incubators/{incubatorId}/fill
export const fillIncubator = async (incubationTime: number, incubatorId: number, eggId: number, hero: HeroType | null) => {
    if (!hero) {
        console.error('Hero is null');
        return null;
    }
    try {
        console.log("api put request: ", incubationTime, " eggId: ",eggId)
        const response = await fetch(`${process.env.REACT_APP_API_URL}/incubators/${incubatorId}/fill`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                'heroName': hero?.name ? hero.name : 'louis',
            },
            body: JSON.stringify({ 
                eggId: eggId,
                incubationTime: incubationTime
            }),
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

// /incubators
export const fetchIncubators = async ( hero: HeroType | null) => {
    if (!hero) {
        console.error('Hero is null');
        return null;
    }
    try {
        const response = await fetch(`${process.env.REACT_APP_API_URL}/incubators`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'heroName': hero?.name ? hero.name : 'louis',
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

// /incubators/{incubatorId}/status
export const fetchIncubatorsStatus = async (incubatorId: number) => {
    try {
        const response = await fetch(`${process.env.REACT_APP_API_URL}/incubators/${incubatorId}/status`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
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
