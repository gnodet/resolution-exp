package org.gnodet.test;

import org.apache.maven.api.*;
import org.apache.maven.api.di.*;
import org.apache.maven.api.plugin.annotations.Mojo;
import org.apache.maven.api.plugin.annotations.Parameter;
import org.apache.maven.api.services.DependencyResolver;
import org.apache.maven.api.services.DependencyResolverRequest;

@Mojo(name = "static-tree")
public class TestMojo implements org.apache.maven.api.plugin.Mojo {

    @Inject
    Project project;

    @Inject
    Session session;

    @Override
    public void execute() {
        Node node = session.getService(DependencyResolver.class)
                .collect(
                        DependencyResolverRequest.build(
                                session, DependencyResolverRequest.RequestType.COLLECT,
                                project, PathScope.MAIN_RUNTIME
                        )).getRoot();
        display(node, "");
    }

    private void display(Node node, String prefix) {
        System.out.println(prefix + node);
        node.getChildren().forEach(c -> display(c, prefix + "  "));
    }

}
