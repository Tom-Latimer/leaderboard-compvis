import type {SubmissionLeaderboardItem} from "../../../../../../types/submissions/submissionLeaderboardItem.ts";
import UpArrowIcon from "../../../../../../assets/up-arrow.svg?react";
import DownArrowIcon from "../../../../../../assets/down-arrow.svg?react";
import "./leaderboard.css";
import React, {useEffect, useRef, useState} from "react";
import DetailsSidebar from "../DetailsSidebar/DetailsSidebar.tsx";

type SortKey = "maxPrecision" | "maxRecall" | "split" | null;
type SortOrder = "asc" | "desc" | null;

interface LeaderboardHeaderProps {
    displayValue: string;
    value: SortKey;
    sortOrder: SortOrder;
    sortKey: string | null;
    className: string;
    onClick: (key: SortKey) => void;
}

const LeaderboardRow = ({rank, submission, onClick}: {
    rank: number,
    submission: SubmissionLeaderboardItem,
    onClick: React.MouseEventHandler<HTMLTableRowElement>
}) => {

    return (
        <tr onClick={onClick}>
            <td className="text-center">{rank}</td>
            <td>{submission.organization}</td>
            <td>{submission.teamName}</td>
            <td>{submission.maxPrecision.toFixed(2)}</td>
            <td>{submission.maxRecall.toFixed(2)}</td>
            <td>{submission.split.toFixed(2)}</td>
        </tr>
    );
}

const LeaderboardHeader = ({displayValue, value, sortOrder, sortKey, className, onClick}:
                           LeaderboardHeaderProps) => {

    return (
        <th className={className} onClick={() => onClick(value)}>
            {displayValue}
            <span>
                {sortKey && sortKey === value ? (sortOrder === "asc" ? <UpArrowIcon className="active-icon"/> :
                        <DownArrowIcon className="active-icon"/>) :
                    <DownArrowIcon/>}
            </span>
        </th>
    );
}

const Leaderboard = ({submissions, sortKey, sortOrder, setSortKey, setSortOrder}:
                     {
                         submissions: SubmissionLeaderboardItem[],
                         sortKey: string | null,
                         sortOrder: SortOrder | null,
                         setSortKey: (key: string | null) => void,
                         setSortOrder: (order: SortOrder | null) => void
                     }) => {

    //ref to control the details sidebar modal
    const dialogRef = useRef<HTMLDialogElement | null>(null);

    const [selectedSubmissionId, setSelectedSubmissionId] = useState<string | null>(null);

    //set initial sort
    useEffect(() => {
        setSortKey("maxPrecision");
    }, []);

    const handleSort = (key: SortKey) => {
        if (sortKey === key) {
            setSortOrder(sortOrder === "asc" ? "desc" : "asc");
        } else {
            setSortKey(key as string);
            setSortOrder("desc");
        }
    }

    const openAndSetModalContent = (submissionId: string) => {
        setSelectedSubmissionId(submissionId);

        dialogRef.current?.showModal();
    }

    return (
        <>
            <div className="leaderboard-container">
                <div className="leaderboard-wrapper">
                    <table className="leaderboard">
                        <thead>
                        <tr>
                            <th className="text-center">Rank</th>
                            <th>Organization</th>
                            <th>Team Name</th>
                            <LeaderboardHeader
                                displayValue="Max Precision"
                                value="maxPrecision"
                                sortOrder={sortOrder}
                                sortKey={sortKey}
                                className="sortable-header"
                                onClick={handleSort}/>
                            <LeaderboardHeader
                                displayValue="Max Recall"
                                value="maxRecall"
                                sortOrder={sortOrder}
                                sortKey={sortKey}
                                className="sortable-header"
                                onClick={handleSort}/>
                            <LeaderboardHeader
                                displayValue="Split"
                                value="split"
                                sortOrder={sortOrder}
                                sortKey={sortKey}
                                className="sortable-header"
                                onClick={handleSort}/>
                        </tr>
                        </thead>
                        <tbody>
                        {submissions.length !== 0 ? (

                            submissions.map((sub) => (
                                <LeaderboardRow
                                    key={sub.submissionId}
                                    rank={sub.rank + 1}
                                    submission={sub}
                                    onClick={() => openAndSetModalContent(sub.submissionId)}
                                />
                            ))) : (
                            <tr>
                                <td colSpan={6}></td>
                            </tr>
                        )}
                        </tbody>
                    </table>
                </div>
            </div>
            <DetailsSidebar submissionId={selectedSubmissionId} ref={dialogRef}></DetailsSidebar>
        </>
    );
}

export default Leaderboard;