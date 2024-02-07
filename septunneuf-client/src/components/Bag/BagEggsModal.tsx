import { Modal } from "@mui/material";

const BagEggsModal = ({ open, onClose }: { open: boolean; onClose: () => void; }) => {

    
    return (
        <>
             <Modal
                open={open}
                onClose={onClose}
                aria-labelledby="modal-title"
                aria-describedby="modal-description"
            >
                <div className="absolute top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 w-400 bg-white shadow-lg p-4 rounded-md">
                   <div>Afficher les oeufs et un nouton ajouter</div>

                </div>
            </Modal>
        </>
    );
};

export default BagEggsModal;