# CQRS & Event Sourcing w/ Spring Boot and Apache Kafka.

This is the case study project built during the course [Learn how to create microservices that are based on CQRS & Event Sourcing. Powered by Spring Boot and Apache Kafka.](https://www.udemy.com/course/java-microservices-cqrs-event-sourcing-with-kafka/) 

# Tools

Use Visual Studio code with remote containers extension - a configuration is included in this repository.

![Visual Studio Code workspace layout](./docs/img/vsc-workspace.png "Visual Studio Code workspace layout")

# Debug and test

- Ensure that MongoDB, MySQL, Kafka, and Zookeeper containers are running and reachable from your machine.
  - A [docker compose configuration file](./setup/docker-compose.yaml) is provided: use it!
- Run both `AccountCommandApplication`  and `AccountQueryApplication`
- Use the sample REST invocations, like in [this folder](./rest-client/) (using [VSC REST Client extension](https://marketplace.visualstudio.com/items?itemName=humao.rest-client)).

# Limitations and disclaimer

This is only a case-study for practicing the concepts - it is not production ready in any way. 

For example, publishing events is done in not transactional way (e.g., there is no Outbox pattern implemented).

You probably want to use existing libraries or frameworks for doing proper CQRS applications, see [this video](https://www.youtube.com/watch?v=_V3C-e0gKoI&ab_channel=DevoxxPoland) for ideas.

# TODO
- Error handling is missing - using a central `ControllerAdvice` makes sense
- Unit and integrations are missing - yep, sadly the course was not supporting automatic testing but it should not be a problem to retrofit these.
  - integration tests are the most difficult since there are many moving pieces around
  - unit tests should be easy to do 

# References

CQRS is a technical architecture for supporting Domain-Driven Design, so you may want to dive deep in this approach and understand its consequences.
Besides google'ing and watching videos, you may want to read the following:
- [Domain Driven Design](https://www.amazon.co.uk/Domain-Driven-Design-Tackling-Complexity-Software/dp/0321125215) - this is the historical book from Eric Evans, it is not really practical but still valuable. 
- [Learning Domain-Driven Design](https://www.amazon.co.uk/Learning-Domain-Driven-Design-Aligning-Architecture/dp/1098100131) - this is a great summary, connecting the business modelling and architectural implementation with a lot of examples. It is not providing you with a ready-to-use solution but it gives you the needed high-level views for understanding how business agility and software architectures relates in the DDD context.
