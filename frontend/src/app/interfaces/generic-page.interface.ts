export interface genericPage<T> {
    content: T[];
    hasNext: boolean;
    size: number;
    totalPages: number;
}