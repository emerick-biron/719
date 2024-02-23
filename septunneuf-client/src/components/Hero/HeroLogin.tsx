import { useRecoilState } from 'recoil';
import { heroState } from '../../recoil/HeroContext';
import HeroForm from './HeroForm';
import { useNavigate } from 'react-router-dom';

const HeroLogin = () => {
	const [, setUser] = useRecoilState(heroState);
	const navigate = useNavigate(); 
	
  	const login = async (formData: any) => {
		try {
			const heroName: string = formData.name;
			const response = await fetch(`${process.env.REACT_APP_API_URL}/heroes/${heroName}/details`);

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
			console.log('Erreur lors de la connexion :', error);
		}
	};

  	return (
		<>
			<HeroForm 
				fields={[
					{ name: "name", placeholder: "Nom" }
				]}
				buttonText="Connexion"
				isLogin={true}
				onSubmit={login}
			/>
		</>
	);
};

export default HeroLogin;
