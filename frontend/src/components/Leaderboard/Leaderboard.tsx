import {SubmissionLeaderboardItem} from "../../types/submissions/submissionLeaderboardItem.ts";
import UpArrowIcon from "../../assets/up-arrow.svg?react";
import DownArrowIcon from "../../assets/down-arrow.svg?react";
import "./leaderboard.css";
import {useEffect} from "react";

type SortKey = "maxPrecision" | "maxRecall" | "split" | null;
type SortOrder = "asc" | "desc" | null;

const LeaderboardRow = ({rank, submission}: { rank: number, submission: SubmissionLeaderboardItem }) => {

    return (
        <tr>
            <td className="text-center">{rank}</td>
            <td>{submission.getName()}</td>
            <td>{submission.maxPrecision.toFixed(2)}</td>
            <td>{submission.maxRecall.toFixed(2)}</td>
            <td>{submission.split.toFixed(2)}</td>
        </tr>
    );
}

const LeaderboardHeader = ({displayValue, value, sortOrder, sortKey, className, onClick}:
                           {
                               displayValue: string,
                               value: SortKey,
                               sortOrder: SortOrder,
                               sortKey: string | null,
                               className: string,
                               onClick: (key: SortKey) => void,
                           }) => {

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
    //set initial sort
    useEffect(() => {
        setSortKey("maxPrecision");
        //sortKey = "maxPrecision";
    }, []);

    const handleSort = (key: SortKey) => {
        if (sortKey === key) {
            setSortOrder(sortOrder === "asc" ? "desc" : "asc");
        } else {
            setSortKey(key as string);
            setSortOrder("desc");
        }
    }

    return (
        <div className="leaderboard-container">
            <div className="leaderboard-wrapper">
                <table className="leaderboard">
                    <thead>
                    <tr>
                        <th className="text-center">Rank</th>
                        <th>Name</th>
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
                            />
                        ))) : (
                        <tr>
                            <td colSpan={4}></td>
                        </tr>
                    )}
                    </tbody>
                </table>
            </div>
        </div>
    );
}

export default Leaderboard;