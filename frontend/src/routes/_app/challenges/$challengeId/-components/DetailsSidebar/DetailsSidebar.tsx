import React, {useEffect, useRef, useState} from "react";
import axios from "axios";
import {getSubmissionDetailsForLeaderboard} from "../../../../../../api/challenges/submissionApi.ts";
import type {Submission} from "../../../../../../types/submissions/submission.ts";
import "./details-sidebar.css";
import XIcon from "../../../../../../assets/x.svg?react";

const DetailsSidebar = ({submissionId, ref}: {
    submissionId: string | null,
    ref: React.RefObject<HTMLDialogElement | null>
}) => {

    //store the submission details between renders if submissionId is not changed
    const [submission, setSubmission] = useState<Submission | null>(null);

    //reference for AbortController
    const abortControllerRef = useRef<AbortController>(null);

    useEffect(() => {
        const fetchData = async () => {
            //abort if another request is in progress
            if (abortControllerRef.current) {
                abortControllerRef.current.abort();
            }

            //pass new abort signal to api call
            abortControllerRef.current = new AbortController();
            const {signal} = abortControllerRef.current;

            try {

                //guard against null
                if (!submissionId) return;

                const data = await getSubmissionDetailsForLeaderboard(submissionId, signal);
                setSubmission(data);
                ref.current?.showModal();
            } catch (error) {
                if (error instanceof Error && error.name !== 'AbortError' && !axios.isCancel(error)) {
                    throw error;
                }
            }

        };

        //call function immediately
        fetchData();

        //cleanup function to kill api call in progress
        return () => {
            abortControllerRef.current?.abort();
            setSubmission(null);
        };
    }, [submissionId]);

    //close the popup
    const handleClose = () => {
        if (ref.current) {
            ref.current?.close();
        }
    }

    return (
        <>
            <dialog className="sidebar-container" ref={ref}
                    onClick={(e) => {
                        if (e.currentTarget === e.target) {
                            handleClose();
                        }
                    }}>

                <div className="details-sidebar">
                    <div className="details-sidebar-header">
                        <div className="header-content">
                            <h2>Submission Details</h2>
                            <button className="x-button" onClick={handleClose}>
                                <XIcon/>
                            </button>
                        </div>
                    </div>
                    <div className="details-sidebar-body">
                        {submission && (
                            <>
                                <div className="section">
                                    <h3 className="section-header">Overview</h3>
                                    <div className="section-body">
                                        <div className="overview-section-item">
                                            <p className="label">Organization</p>
                                            <p className="value">{submission.organization}</p>
                                        </div>
                                        <div className="overview-section-item">
                                            <p className="label">Team Name</p>
                                            <p className="value">{submission.teamName}</p>
                                        </div>
                                        <div className="overview-section-item">
                                            <p className="label">Precision</p>
                                            <p className="value">{submission.maxPrecision}</p>
                                        </div>
                                        <div className="overview-section-item">
                                            <p className="label">Recall</p>
                                            <p className="value">{submission.maxRecall}</p>
                                        </div>
                                        <div className="overview-section-item">
                                            <p className="label">Split</p>
                                            <p className="value">{submission.split}</p>
                                        </div>
                                    </div>
                                </div>
                                <div className="section">
                                    <h3 className="section-header">Team Members</h3>
                                    <div className="section-body">
                                        <div className="tm-details-table-wrapper">
                                            <table className="tm-details-table">
                                                <thead>
                                                <tr>
                                                    <th>First Name</th>
                                                    <th>Last Name</th>
                                                    <th>Email</th>
                                                    <th className="text-center">Primary Contact</th>
                                                </tr>
                                                </thead>
                                                <tbody>
                                                {submission.teamMembers && submission.teamMembers.length > 0 && (
                                                    submission.teamMembers.map((member, index) => (
                                                        <tr key={index}>
                                                            <td>{member.firstName}</td>
                                                            <td>{member.lastName}</td>
                                                            <td>{member.email}</td>
                                                            <td className="text-center">
                                                                <input
                                                                    type="radio"
                                                                    checked={member.isContact}
                                                                    disabled
                                                                />
                                                            </td>
                                                        </tr>
                                                    ))
                                                )}
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                            </>
                        )}
                    </div>
                </div>

            </dialog>
        </>
    );
}

export default DetailsSidebar;