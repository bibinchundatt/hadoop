package org.apache.hadoop.yarn.server.nodemanager.containermanager.windows.runtime;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.yarn.server.nodemanager.Context;
import org.apache.hadoop.yarn.server.nodemanager.containermanager.container.Container;
import org.apache.hadoop.yarn.server.nodemanager.containermanager.runtime.ContainerExecutionException;
import org.apache.hadoop.yarn.server.nodemanager.containermanager.runtime.ContainerRuntimeContext;

import java.util.Map;

/**
 * This class {@link DefaultWindowsContainerRuntime} is an implementation of
 * {@link WindowsContainerRuntime}  to handle the container process life cycle.
 */
public class DefaultWindowsContainerRuntime implements WindowsContainerRuntime {

  @Override
  public void initialize(Configuration conf, Context nmContext)
      throws ContainerExecutionException {

  }

  @Override
  public boolean isRuntimeRequested(Map<String, String> env) {
    return false;
  }

  @Override
  public void prepareContainer(ContainerRuntimeContext ctx)
      throws ContainerExecutionException {

  }

  @Override
  public void launchContainer(ContainerRuntimeContext ctx)
      throws ContainerExecutionException {

  }

  @Override
  public void relaunchContainer(ContainerRuntimeContext ctx)
      throws ContainerExecutionException {

  }

  @Override
  public void signalContainer(ContainerRuntimeContext ctx)
      throws ContainerExecutionException {

  }

  @Override
  public void reapContainer(ContainerRuntimeContext ctx)
      throws ContainerExecutionException {

  }

  @Override
  public String[] getIpAndHost(Container container)
      throws ContainerExecutionException {
    return new String[0];
  }
}
