import type {OrderedItem} from "../pagination/orderedItem.ts";

export interface SubmissionLeaderboardItem extends OrderedItem {
    submissionId: string;
    organization: string;
    teamName: string;
    maxPrecision: number;
    maxRecall: number;
    split: number;
}