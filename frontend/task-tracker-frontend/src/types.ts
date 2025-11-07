export type TaskDto = {
	id: number;
	title: string;
	description: string;
	status: string;
	priority: string;
	dueDate: string | null; // TODO: dueDate might be mandatory later on
	createdAt: string;
	updatedAt: string;
};
