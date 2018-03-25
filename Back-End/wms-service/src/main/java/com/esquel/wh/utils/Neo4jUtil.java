package com.esquel.wh.utils;

import static org.neo4j.helpers.collection.MapUtil.map;

import java.io.File;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import org.jetbrains.annotations.NotNull;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Result;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphql.GraphQLContext;
import org.neo4j.graphql.GraphQLProcedure;
import org.neo4j.graphql.GraphQLSchemaBuilder;
import org.neo4j.graphql.GraphSchemaScanner;
import org.neo4j.kernel.impl.proc.Procedures;
import org.neo4j.kernel.internal.GraphDatabaseAPI;
import org.neo4j.logging.FormattedLog;
import org.neo4j.logging.FormattedLogProvider;

import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.GraphQL.Builder;
import graphql.GraphQLError;
import graphql.schema.GraphQLSchema;

public class Neo4jUtil {
	private static GraphDatabaseService db;
	private static FormattedLog log = FormattedLogProvider.toPrintWriter(new PrintWriter(System.out))
			.getLog(Neo4jUtil.class);
	private static GraphQL graphql;
	
	private Neo4jUtil(String path) {
		try {
			System.out.println(path);
			File DB_PATH = new File(path);
			GraphDatabaseFactory dbFactory = new GraphDatabaseFactory();
			db = dbFactory.newEmbeddedDatabase(DB_PATH);

			((GraphDatabaseAPI) db).getDependencyResolver().resolveDependency(Procedures.class)
					.registerFunction(GraphQLProcedure.class);
			String schema = Common.ReadFile("C:\\zhanghonl\\WMS\\WMS.txt");
			GraphSchemaScanner.Companion.storeIdl(db,schema);
			GraphQLSchema graphQLSchema = GraphQLSchemaBuilder.buildSchema(db);
			Builder builder = new Builder(graphQLSchema);
			graphql = builder.build();
			

		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}

	private static Neo4jUtil neo4j = null;

	public static Neo4jUtil getInstance() {
		if (neo4j == null) {
			neo4j = new Neo4jUtil("C:\\WMS\\data\\WMS");
		}
		return neo4j;
	}

	public static Neo4jUtil getInstance(String path){
		if (neo4j == null) {
			neo4j = new Neo4jUtil(path);
		}
		return neo4j;
	}
	
	public Result execute(String query, Map json) {
		try {
			Result result = db.execute(query, java.util.Collections.singletonMap("json", (Object) json));
			return result;
		} catch (Exception ex) {
			throw ex;
		} 
	}

	public Node getNode(Long id) {
		return db.getNodeById(id);
	}

	public Map<String, List<Map>> executeQuery(String query, Map<String, Object> arguments) {
		return (Map<String, List<Map>>) getResult(query, arguments).getData();
	}

	@NotNull
	private ExecutionResult getResult(String query, Map<String, Object> arguments) {
		GraphQLContext ctx = new GraphQLContext(db, log, arguments, map());
		return executeQuery(query, arguments, ctx);
	}

	@NotNull
	private ExecutionResult executeQuery(String query, Map<String, Object> arguments, GraphQLContext ctx) {
		System.out.println("query = " + query);
		ExecutionResult result = null;
		try {
			ExecutionInput.Builder executionInput = ExecutionInput.newExecutionInput();
			executionInput.context(ctx);
			executionInput.query(query);
			
			//result = graphql.execute(query, ctx, arguments);
			result = graphql.execute(executionInput);
			Object data = result.getData();
			System.out.println("data = " + data);
			List<GraphQLError> errors = result.getErrors();
			System.out.println("errors = " + errors);
		} catch (Exception t) {
			Result r = db.execute("Match (n:GRN) return n");
			System.out.println("Graphql:" + r.columns().size());
			System.out.println("Error:" + t.getStackTrace());
			throw t;
		}

		System.out.println("extensions = " + ctx.getBackLog());
		return result;
	}

	public Transaction getTransaction() {
		return db.beginTx();
	}

}
