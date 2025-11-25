import React from "react";

export interface DialogHandle {
    showModal: () => void;
    closeModal: () => void;
    setModalContent: (content: React.ReactNode) => void;
}