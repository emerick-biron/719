import { Alert, Snackbar } from "@mui/material"

interface CustomAlertProps {
    open: boolean;
    text: string;
    severty: "success" | "error" | "warning" | "info";
    onClose: () => void;
}
const CustomAlert = (props: CustomAlertProps) => {
    const { open, onClose, severty, text } = props;
    return (
        <Snackbar
            open={open}
            autoHideDuration={2000}
            onClose={onClose}
        >
            <Alert onClose={onClose} severity={severty}>
                {text}
            </Alert>
        </Snackbar>
    );
}

export default CustomAlert;