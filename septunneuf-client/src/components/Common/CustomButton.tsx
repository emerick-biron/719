interface ButtonProps {
    text: string;
    color: string;
    onClick: () => void;
    disabled?: boolean;
}

const CustomButton = (props: ButtonProps ) => {
    const { disabled, text, color, onClick } = props;

    return (
        <button 
            onClick={onClick} 
            style={{ backgroundColor: disabled ? 'gray' : color }} 
            disabled={disabled}
            className="rounded px-4 py-2 m-2"
        >
            {text}
        </button>
    );
};

export default CustomButton;