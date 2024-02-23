// /monsters/{monsterId}/details
export const fetchMonsterDetails = async (monsterId: number) => {
    try {
        const response = await fetch(`${process.env.REACT_APP_API_URL}/monsters/${monsterId}/details`, {
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