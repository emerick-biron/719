import { useRecoilValue } from "recoil";
import CustomButton from "../Common/CustomButton"
import { heroState } from "../../recoil/HeroContext";

const BagTeamMonster = (props:{data:any, onDelete: () => void }) => {
    const hero = useRecoilValue(heroState);

    const handleDeleteMonster = async (monsterId: number) => {
        try {
            const response = await fetch(`${process.env.REACT_APP_API_URL}/inventory/monsters/${monsterId}/remove`, {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    heroName: hero?.name,
                    monsterIds: monsterId
                })
            });
            if (!response.ok) {
                console.log('Erreur lors de la suppression');
            } else {
                props.onDelete();
            }
            const data = await response.json();
            console.log(data);
        } 
        catch (error) {
            console.error('Erreur:', error);
        }
    };

    const handleMoveMonster = async (monsterId: number) => {
        try {
            const response = await fetch(`${process.env.REACT_APP_API_URL}/storage/add`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    heroName: hero?.name,
                    monsterIds: monsterId
                })
            });
            if (!response.ok) {
                console.log('Erreur lors de la suppression');
            } else {
                props.onDelete();
            }
            const data = await response.json();
            console.log(data);
        } 
        catch (error) {
            console.error('Erreur:', error);
        }
    };

    return(
        <div style={{ border: `2px solid ${props.data.color}` }} className="w-1/2 px-2 my-2 flex items-center rounded-md">
            <div className="font-semibold pl-2">{props.data.id} - {props.data.name.toUpperCase()}</div>
            <div className="ml-auto">

                <CustomButton 
                    onClick={() => handleMoveMonster(props.data.id)}
                    text="Deplacer"
                    color="green"
                />
                <CustomButton 
                    onClick={() => handleDeleteMonster(props.data.id)}
                    text="Supprimer"
                    color="red"
                />
            </div>
        </div>
    );
};

export default BagTeamMonster;