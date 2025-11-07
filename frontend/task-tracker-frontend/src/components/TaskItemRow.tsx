import { Button, Col, Row } from "antd";
import type { TaskDto } from "../types";

export const TaskItemRow = ({ task }: { task: TaskDto }) => {
	return (
		<Row style={{ border: "solid 1px black", borderRadius: "10px", margin: "4px" }}>
			<Col flex={1}>
				<Row justify="space-between" align="middle">
					<Col style={{ margin: "2px" }}>{task.title}</Col>
					<Col>
						<Row align="middle">
							<Col style={{ margin: "2px" }}>{task.status}</Col>
							<Col style={{ margin: "2px" }}>
								<Button>Mark as Done</Button>
							</Col>
						</Row>
					</Col>
				</Row>
				<Row justify="space-between">
					<Col style={{ margin: "2px" }}>{task.description}</Col>
					<Col style={{ margin: "2px" }}>
						<Button>Delete Task</Button>
					</Col>
				</Row>
			</Col>
		</Row>
	);
};
