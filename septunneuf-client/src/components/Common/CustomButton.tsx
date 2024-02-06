interface ButtonProps {
    text?: string;
    color?: string;
    onClick: () => void;
    disabled?: boolean;
}

const CustomButton = (props: ButtonProps ) => {
    const { disabled, text, color, onClick } = props;

    return (
        <button 
            onClick={onClick} 
            disabled={disabled}
            className={`rounded px-4 py-2 m-2 ${
                disabled
                  ? 'bg-gray-500 cursor-not-allowed'
                  : `bg-${color}-300 hover:bg-${color}-400 cursor-pointer`
              }`}
        >
            {text}
        </button>
    );
};

export default CustomButton;