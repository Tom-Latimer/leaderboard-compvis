import {type ComponentType, startTransition, useEffect, useRef, useState} from "react";
import axios from "axios";
import type {PagedResponse} from "../../types/pagination/pagedResponse.ts";
import type {OrderedItem} from "../../types/pagination/orderedItem.ts";
import LeftChevronIcon from "../../assets/left-chevron.svg?react";
import RightChevronIcon from "../../assets/right-chevron.svg?react";
import "./paginated-wrapper.css";

type SortOrder = "asc" | "desc";

type PaginatedProps<T extends OrderedItem> = {
    fetchPage: (
        page: number,
        pageSize: number,
        sortKey: string | null,
        sortOrder: string | null,
        signal: AbortSignal) => Promise<PagedResponse<T>>;
    pageSize?: number;
    renderComponent: ComponentType<{
        items: T[];
        sortKey: string | null;
        sortOrder: SortOrder | null;
        setSortKey: (key: string | null) => void;
        setSortOrder: (order: SortOrder | null) => void;
    }>;
}

export function PaginatedWrapper<T extends OrderedItem>(
    {
        fetchPage,
        pageSize = 25,
        renderComponent: Component
    }: PaginatedProps<T>) {
    //states to track api paged data
    const [items, setItems] = useState<T[]>([]);
    const [currentPage, setCurrentPage] = useState(0);
    const [totalPages, setTotalPages] = useState(0);

    //states for sorting the data
    const [sortKey, setSortKey] = useState<string | null>(null);
    const [sortOrder, setSortOrder] = useState<SortOrder | null>("desc");

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
                const data = await fetchPage(currentPage, pageSize, sortKey, sortOrder, signal);

                setItems(data.content);
                setTotalPages(data.totalPages);
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
        };

    }, [currentPage, sortKey, sortOrder]);

    const nextPage = () => {
        startTransition(() => {
            setCurrentPage(currentPage + 1);
        });
    }

    const prevPage = () => {
        startTransition(() => {
            setCurrentPage(currentPage - 1);
        });
    }

    return (
        <div>
            <Component
                items={items}
                sortKey={sortKey}
                sortOrder={sortOrder}
                setSortKey={setSortKey}
                setSortOrder={setSortOrder}
            />
            <div className="pagination-container">
                <ul className="pagination-controls">
                    <li>
                        <button className="page-button" disabled={currentPage === 0} onClick={prevPage}>
                            <LeftChevronIcon/>
                        </button>
                    </li>
                    <li>
                        <p className="page-list-item">Page {currentPage + 1} / {totalPages}</p>
                    </li>
                    <li>
                        <button className="page-button" disabled={(currentPage + 1) === totalPages} onClick={nextPage}>
                            <RightChevronIcon/>
                        </button>
                    </li>
                </ul>
            </div>
        </div>
    );
}