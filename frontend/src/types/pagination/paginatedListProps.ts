export type SortOrder = "asc" | "desc";

export interface PaginatedListProps<T> {
    items: T[],
    sortKey: string | null,
    sortOrder: SortOrder | null,
    setSortKey: (key: string | null) => void,
    setSortOrder: (order: SortOrder | null) => void
}