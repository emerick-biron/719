import { Drawer, IconButton } from "@mui/material";
import { useState } from "react";
import SettingsIcon from '@mui/icons-material/Settings';
import { useRecoilState } from "recoil";
import { heroState } from "../../recoil/HeroContext";
import CloseIcon from '@mui/icons-material/Close';
import { useNavigate } from "react-router-dom";

const CustomDrawer = () => {
    const [open, setOpen] = useState(false);
    const [hero, setHero] = useRecoilState(heroState);
    const navigate = useNavigate();

    const logout = () => {
        setHero(null); 
        localStorage.removeItem('hero');
        navigate("/login-hero")
    };

    const toggleDrawer = (status: boolean) => () => {
        setOpen(status);
    };

    return (
        <>
            <IconButton
                edge="end"
                color="inherit"
                onClick={toggleDrawer(true)}
                sx={{ padding: 0, margin: 0 }}
            >
                <SettingsIcon />
            </IconButton>
            <Drawer 
                anchor="right" 
                open={open} 
                onClose={toggleDrawer(false)}
                PaperProps={{ className: "rounded-lg" }}
            >
                <div
                    role="presentation"
                    onClick={toggleDrawer(false)}
                    onKeyDown={toggleDrawer(false)}
                    className="w-80"
                >
                    <div className="flex justify-between px-4 py-6 font-semibold border-b">
                        <p>Paramètres</p>
                        <IconButton
                            edge="end"
                            color="inherit"
                            onClick={toggleDrawer(false)}
                            sx={{ padding: 0, margin: 0, color: 'red' }}
                        >
                            <CloseIcon />
                        </IconButton>
                    </div>
                    <div className="flex justify-between px-4 py-6 font-semibold">
                        <p>Nom</p>
                        <p>{hero?.name.toUpperCase()}</p>
                    </div>
                    <div className="flex justify-between px-4 py-6 font-semibold">
                        <p>Couleur</p>
                        <p>{hero?.color}</p>
                    </div>
                    <div className="px-4 text-center">
                        <button 
                            onClick={logout} 
                            className="bg-red-400 px-8 py-2 font-semibold rounded-lg hover:bg-red-600"
                        >Déconnexion</button>
                    </div>
                </div>
            </Drawer>
        </>
    );
}

export default CustomDrawer;
