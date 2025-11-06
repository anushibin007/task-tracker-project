import { Button, Col, Input, Row, Select } from "antd";
const { TextArea } = Input;
import "./App.css";
import Title from "antd/es/typography/Title";
import { useEffect, useState } from "react";

function App() {
	const [taskTitle, setTaskTitle] = useState("");
	const [taskDescription, setTaskDescription] = useState("");
	const [taskPriority, setTaskPriority] = useState("LOW");

	useEffect(() => {
		console.log(taskPriority);
	}, [taskPriority]);

	const createATodo = () => {
		fetch("http://localhost:8080/api/tasks", {
			method: "POST",
			headers: {
				"Content-Type": "application/json",
			},
			body: JSON.stringify({
				title: taskTitle,
				description: taskDescription,
				priority: taskPriority,
				status: "TODO",
			}),
		});
	};

	return (
		<>
			<Row justify="center">
				<Col>
					<Title level={3}>Task Tracker Project</Title>
				</Col>
			</Row>
			<Row>
				<Col flex={1}>
					<Row>
						<Col flex={1} span={18} style={{ margin: "2px" }}>
							<Input
								value={taskTitle}
								onChange={(e) => {
									setTaskTitle(e.target.value);
								}}
								placeholder="Task title"
							/>
						</Col>
						<Col flex={1} span={6} style={{ margin: "2px" }}>
							<Select
								style={{ width: "100%" }}
								options={[
									{ value: "LOW", label: "Low" },
									{ value: "MEDIUM", label: "Medium" },
									{ value: "HIGH", label: "High" },
								]}
								value={taskPriority}
								onChange={(e) => {
									setTaskPriority(e);
								}}
							/>
						</Col>
					</Row>
					<Row>
						<Col flex={1} style={{ margin: "2px" }}>
							<TextArea
								value={taskDescription}
								onChange={(e) => {
									setTaskDescription(e.target.value);
								}}
								placeholder="Task description"
							/>
						</Col>
					</Row>
					<Row justify="center">
						<Col style={{ margin: "2px" }}>
							<Button onClick={createATodo} type="primary">
								Create Task
							</Button>
						</Col>
					</Row>
				</Col>
			</Row>
		</>
	);
}

export default App;
