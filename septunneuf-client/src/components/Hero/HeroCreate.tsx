import { useRecoilState } from 'recoil';
import { heroState } from '../../recoil/HeroContext';
import HeroForm from './HeroForm';
import { useNavigate } from 'react-router-dom';

const HeroCreate = () => {
    const [, setUser] = useRecoilState(heroState);
    const navigate = useNavigate(); 

    const createHero = async (formData: any) => {
        try {
            console.log(formData);
            const response = await fetch(`${process.env.REACT_APP_API_URL}/heroes/create`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
                body: JSON.stringify(formData),
            });

            if (response.ok) {
				const responseData = await response.json();
				const { name, color } = responseData;
				setUser({ name, color, money: 25});
				localStorage.setItem('hero', JSON.stringify({ name, color, money: 25}));
                navigate('/stock');
			} else {
				console.log('Erreur lors de la connexion');
            }
        } catch (error) {
            console.error('Erreur lors de la création du héros :', error);
        }
    };

  return (
    <>
        <HeroForm 
            fields={[
                { name: "name", placeholder: "Nom" }
            ]}
            buttonText="Créer un compte"
            isLogin={false}
            onSubmit={createHero}
        />
        <div className="flex justify-center space-x-2">
</div>
    </>
  );
};

export default HeroCreate;
