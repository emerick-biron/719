import { useEffect, useState } from "react";

const BagEgg = (props:{eggId: number}) => {
    const [egg, setEgg] = useState<any | null>([]);

    useEffect(() => {
        const fetchEgg = async (eggId: number) => {
            try {
                const response = await fetch(`${process.env.REACT_APP_API_URL}/eggs/${eggId}/details`, {
                    method: 'GET',
                    headers: {
                        'Content-Type': 'application/json',
                    }
                });
                if (!response.ok) {
                    console.log('Erreur lors de la récupération des données');
                }
                const data = await response.json();
                console.log("fetchIncubatorStatus data:", data[0]);
                setEgg(data[0]);
            } 
            catch (error) {
                console.error('Erreur:', error);
            }
        };
        fetchEgg(props.eggId);
    }, []);

    return(
        <div className="flex flex-col items-center justify-center">
            <div style={{ background: egg?.color}} className="m-2 mt-4 mb-0 w-32 h-32 rounded-full flex items-center justify-center" >
                {egg?.incubationTime}
            </div>
        </div>
    );
}

export default BagEgg;