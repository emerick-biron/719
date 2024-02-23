import { useState } from "react";
import { Link } from "react-router-dom";

interface HeroFormProps {
    fields: { name: string, placeholder: string }[],
    buttonText: string,
    isLogin: boolean,
    onSubmit: (formData: any) => void;
}

const HeroForm = (props: HeroFormProps) => {
    const defaultColor = "#6590D5"; // Couleur par défaut
    const [formData, setFormData] = useState<{ [key: string]: string }>({
        color: defaultColor // Définir la couleur par défaut
    });

    const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setFormData((prevData) => ({
            ...prevData,
            [e.target.name]: e.target.value,
        }));
    };

    const handleColorChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setFormData((prevData) => ({
            ...prevData,
            color: e.target.value,
        }));
    };

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        props.onSubmit(formData);
    };

    return (
        <div className="bg-gray-100 min-h-screen flex flex-col">
            <div className="container max-w-sm mx-auto flex-1 flex flex-col items-center justify-center px-2">
                <div className="bg-white px-6 py-8 rounded shadow-md text-black w-full">
                    <form onSubmit={handleSubmit}>
                        <h1 className="mb-8 text-3xl text-center">{props.buttonText}</h1>
                        {props.fields.map((field) => (
                            <input
                                key={field.name}
                                type="text"
                                className="block border border-grey-light w-full p-3 rounded mb-4"
                                name={field.name}
                                placeholder={field.placeholder}
                                onChange={handleInputChange}
                            />
                        ))}
                        {!props.isLogin ? (
                            <div className="flex space-x-2 items-center border p-2 mb-2 rounded-md">
                                <input
                                    id="nativeColorPicker"
                                    type="color"
                                    className="block w-12 h-12 p-0 border"
                                    value={formData.color}
                                    onChange={handleColorChange}
                                />
                                <label htmlFor="nativeColorPicker" className="text-gray-700">
                                    Choisissez une couleur
                                </label>
                            </div>
                        ) : null}
                        <button
                            type="submit"
                            className="w-full text-center py-3 rounded bg-green-300 hover:bg-green-400 focus:outline-none my-1"
                        >
                            {props.isLogin ? "Connexion" : "Créer un compte"}
                        </button>
                    </form>
                </div>

                <div className="text-grey-dark mt-6">
                    Possédez-vous déjà un compte ?
                    <Link to={`/${props.isLogin ? "create-hero" : "login-hero"}`} className="no-underline border-b border-blue text-blue">
                        {props.isLogin ? "Créer un compte" : "Connexion"}
                    </Link>
                </div>
            </div>
        </div>
    );
}

export default HeroForm;
