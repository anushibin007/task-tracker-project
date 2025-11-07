import { Button, Col, Input, Row, Select } from "antd";
const { TextArea } = Input;
import "./App.css";
import Title from "antd/es/typography/Title";
import { useEffect, useState } from "react";
import { TaskItemRow } from "./components/TaskItemRow";
import type { TaskDto } from "./types";
import { BACKEND_ROOT } from "./Constants";

function App() {
	const [taskTitle, setTaskTitle] = useState("");
	const [taskDescription, setTaskDescription] = useState("");
	const [taskPriority, setTaskPriority] = useState("LOW");

	const [allTodos, setAllTodos] = useState<TaskDto[]>([]);

	useEffect(() => {
		console.log(taskPriority);
	}, [taskPriority]);

	useEffect(() => {
		fetchAllTodos();
	}, []);

	const createATodo = async () => {
		await fetch(`${BACKEND_ROOT}/api/tasks`, {
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

	const handleCreateATodo = async () => {
		await createATodo().then(() => {
			setTaskTitle("");
			setTaskDescription("");
			setTaskPriority("LOW");
		});
		fetchAllTodos();
	};

	const fetchAllTodos = async () => {
		await fetch(`${BACKEND_ROOT}/api/tasks`, {
			method: "GET",
			headers: {
				"Content-Type": "application/json",
			},
		})
			.then((resp) => resp.json())
			.then((data) => {
				setAllTodos(data.content);
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
							<Button onClick={handleCreateATodo} type="primary">
								Create Task
							</Button>
						</Col>
					</Row>
				</Col>
			</Row>
			{
				// Existing Tasks below
			}
			<Row style={{ border: "solid 1px black", borderRadius: "10px" }}>
				<Col span={24}>
					<Row>
						<Col style={{ margin: "2px" }}>
							<Title style={{ margin: "2px" }} level={4}>
								Your Tasks
							</Title>
						</Col>
					</Row>
					{allTodos.map((task) => (
						<TaskItemRow key={task.id} task={task} fetchAllTodos={fetchAllTodos} />
					))}
				</Col>
			</Row>
		</>
	);
}

export default App;
