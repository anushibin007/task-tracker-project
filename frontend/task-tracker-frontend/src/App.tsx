import { Button, Col, Input, Row, Select } from "antd";
const { TextArea } = Input;
import "./App.css";
import Title from "antd/es/typography/Title";

function App() {
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
							<Input placeholder="Task title" />
						</Col>
						<Col flex={1} span={6} style={{ margin: "2px" }}>
							<Select
								style={{ width: "100%" }}
								options={[
									{ value: "LOW", label: "Low" },
									{ value: "MEDIUM", label: "Medium" },
									{ value: "HIGH", label: "High" },
								]}
							/>
						</Col>
					</Row>
					<Row>
						<Col flex={1} style={{ margin: "2px" }}>
							<TextArea placeholder="Task description" />
						</Col>
					</Row>
					<Row justify="center">
						<Col style={{ margin: "2px" }}>
							<Button type="primary">Create Task</Button>
						</Col>
					</Row>
				</Col>
			</Row>
		</>
	);
}

export default App;
