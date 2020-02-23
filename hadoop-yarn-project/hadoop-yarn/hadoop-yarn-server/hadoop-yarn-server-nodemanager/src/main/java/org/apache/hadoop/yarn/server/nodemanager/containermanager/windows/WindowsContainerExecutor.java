package org.apache.hadoop.yarn.server.nodemanager.containermanager.windows;

import org.apache.hadoop.yarn.exceptions.ConfigurationException;
import org.apache.hadoop.yarn.server.nodemanager.ContainerExecutor;
import org.apache.hadoop.yarn.server.nodemanager.Context;
import org.apache.hadoop.yarn.server.nodemanager.containermanager.windows.runtime.WindowsContainerRuntime;
import org.apache.hadoop.yarn.server.nodemanager.executor.ContainerLivenessContext;
import org.apache.hadoop.yarn.server.nodemanager.executor.ContainerReapContext;
import org.apache.hadoop.yarn.server.nodemanager.executor.ContainerSignalContext;
import org.apache.hadoop.yarn.server.nodemanager.executor.ContainerStartContext;
import org.apache.hadoop.yarn.server.nodemanager.executor.DeletionAsUserContext;
import org.apache.hadoop.yarn.server.nodemanager.executor.LocalizerStartContext;

import java.io.IOException;

public class WindowsContainerExecutor extends ContainerExecutor {


  private WindowsContainerRuntime linuxContainerRuntime;
  private Context nmContext;

  @Override
  public void init(Context nmContext) throws IOException {

  }

  @Override
  public void startLocalizer(LocalizerStartContext ctx)
      throws IOException, InterruptedException {

  }

  @Override
  public int launchContainer(ContainerStartContext ctx)
      throws IOException, ConfigurationException {
    return 0;
  }

  @Override
  public int relaunchContainer(ContainerStartContext ctx)
      throws IOException, ConfigurationException {
    return 0;
  }

  @Override
  public boolean signalContainer(ContainerSignalContext ctx)
      throws IOException {
    return false;
  }

  @Override
  public boolean reapContainer(ContainerReapContext ctx) throws IOException {
    return false;
  }

  @Override
  public void deleteAsUser(DeletionAsUserContext ctx)
      throws IOException, InterruptedException {

  }

  @Override
  public void symLink(String target, String symlink) throws IOException {

  }

  @Override
  public boolean isContainerAlive(ContainerLivenessContext ctx)
      throws IOException {
    return false;
  }
}
