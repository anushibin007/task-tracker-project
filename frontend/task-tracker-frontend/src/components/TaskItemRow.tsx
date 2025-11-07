import { Button, Col, Row } from "antd";
import type { TaskDto } from "../types";

// TODO -> IN_PROGRESS -> DONE

export const TaskItemRow = ({ task }: { task: TaskDto }) => {
	const getPrimaryActionButtonText = () => {
		if (task.status === "TODO") {
			return "Start progress";
		} else if (task.status === "IN_PROGRESS") {
			return "Mark as done";
		} else if (task.status === "DONE") {
			return "Reopen";
		}
	};

	const updateTaskStatus = () => {
		var newStatus = "";
		if (task.status === "TODO") {
			newStatus = "IN_PROGRESS";
		} else if (task.status === "IN_PROGRESS") {
			newStatus = "DONE";
		} else if (task.status === "DONE") {
			newStatus = "TODO";
		}

		fetch(`http://localhost:8080/api/tasks/${task.id}/status`, {
			method: "PATCH",
			headers: {
				"Content-Type": "application/json",
			},
			body: JSON.stringify({
				status: newStatus,
			}),
		});
	};

	const deleteTask = () => {
		fetch(`http://localhost:8080/api/tasks/${task.id}`, {
			method: "DELETE",
			headers: {
				"Content-Type": "application/json",
			},
		});
	};

	const handlePrimaryActionButtonClicked = () => {
		updateTaskStatus();
	};

	const handleDeleteTask = () => {
		deleteTask();
	};

	return (
		<Row style={{ border: "solid 1px black", borderRadius: "10px", margin: "4px" }}>
			<Col flex={1}>
				<Row justify="space-between" align="middle">
					<Col style={{ margin: "2px" }}>{task.title}</Col>
					<Col>
						<Row align="middle">
							<Col style={{ margin: "2px" }}>{task.status}</Col>
							<Col style={{ margin: "2px" }}>
								<Button onClick={handlePrimaryActionButtonClicked}>
									{getPrimaryActionButtonText()}
								</Button>
							</Col>
						</Row>
					</Col>
				</Row>
				<Row justify="space-between">
					<Col style={{ margin: "2px" }}>{task.description}</Col>
					<Col style={{ margin: "2px" }}>
						<Button onClick={handleDeleteTask}>Delete Task</Button>
					</Col>
				</Row>
			</Col>
		</Row>
	);
};
