// /heroes/create
interface Hero {
    name: string,
    color: string
}

export const heroesCreate = async (hero: Hero) => {
    try {
        const response = await fetch(`${process.env.REACT_APP_API_URL}/heroes/create`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(hero)
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

// /heroes/{heroName}/details
export const fetchHeroesDetails = async (heroName: string) => {
    try {
        const response = await fetch(`${process.env.REACT_APP_API_URL}/heroes/${heroName}/details`, {
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